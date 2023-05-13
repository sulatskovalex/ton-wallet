package me.sulatskovalex.twallet.base

import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.AVFoundation.AVCaptureConnection
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureMetadataOutput
import platform.AVFoundation.AVCaptureMetadataOutputObjectsDelegateProtocol
import platform.AVFoundation.AVCaptureOutput
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionPresetMedium
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVMetadataMachineReadableCodeObject
import platform.AVFoundation.AVMetadataObjectTypeQRCode
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CAShapeLayer
import platform.QuartzCore.kCAFillRuleEvenOdd
import platform.UIKit.UIBarButtonItem
import platform.UIKit.UIBarButtonSystemItem
import platform.UIKit.UIBezierPath
import platform.UIKit.UIColor
import platform.UIKit.UINotificationFeedbackGenerator
import platform.UIKit.UINotificationFeedbackType
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.navigationItem
import platform.darwin.dispatch_get_main_queue
import platform.objc.sel_registerName

class ScannerViewController(
    private val onScan: (scanned: String, onClose: () -> Unit) -> Unit
) : UIViewController(null, null), AVCaptureMetadataOutputObjectsDelegateProtocol {

    private var captureSession: AVCaptureSession? = null
    private var previewLayer: AVCaptureVideoPreviewLayer? = null

    override fun viewDidLoad() {
        super.viewDidLoad()
        navigationItem.rightBarButtonItem =
            UIBarButtonItem(
                barButtonSystemItem = UIBarButtonSystemItem.UIBarButtonSystemItemClose,
                target = this,
                action = sel_registerName("closeDialog"),
            )
        captureSession = AVCaptureSession()
        val session = captureSession ?: return
        session.sessionPreset = AVCaptureSessionPresetMedium
        val device = try {
            AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo) ?: return
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
        try {
            val input = try {
                AVCaptureDeviceInput(device, null)
            } catch (t: Throwable) {
                t.printStackTrace()
                throw t
            }
            val output = AVCaptureMetadataOutput()
            if (session.canAddInput(input) && session.canAddOutput(output)) {
                session.addInput(input)
                session.addOutput(output)
                output.setMetadataObjectsDelegate(this, dispatch_get_main_queue())
                output.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)
            }
            previewLayer = AVCaptureVideoPreviewLayer(session = session)
            val preview = previewLayer ?: return
            preview.frame = view.bounds
            preview.videoGravity = AVLayerVideoGravityResizeAspectFill


            val (width, height) = view.layer.bounds.useContents { size.width to size.height }

            val foreground = UIView(frame = view.bounds)
            foreground.backgroundColor = UIColor(
                red = 0.0,
                green = 0.0,
                blue = 0.0,
                alpha = 0.75,
            )
            val maskLayer = CAShapeLayer()
            maskLayer.frame = foreground.bounds
            maskLayer.fillColor = UIColor.blackColor.CGColor
            maskLayer.fillRule = kCAFillRuleEvenOdd
            val boxWidth = width / 1.3
            val path = UIBezierPath.bezierPathWithRect(rect = foreground.bounds)
            path.appendPath(
                UIBezierPath.bezierPathWithRoundedRect(
                    rect = CGRectMake(
                        x = width / 2 - boxWidth / 2,
                        y = boxWidth,
                        width = boxWidth,
                        height = boxWidth,
                    ),
                    cornerRadius = 16.0,
                )
            )
            maskLayer.path = path.CGPath
            foreground.layer.mask = maskLayer
            view.layer.addSublayer(preview)
            view.addSubview(foreground)
            session.startRunning()
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
    }

    @ObjCAction
    fun closeDialog() {
        captureSession?.stopRunning()
        dismissViewControllerAnimated(flag = true, completion = null)
    }

    override fun viewWillAppear(animated: Boolean) {
        super.viewWillAppear(animated)
        if (captureSession?.isRunning() == false) {
            captureSession?.startRunning()
        }
    }

    override fun viewWillDisappear(animated: Boolean) {
        super.viewWillDisappear(animated)
        if (captureSession?.isRunning() == true) {
            captureSession?.stopRunning()
        }
    }

    override fun prefersStatusBarHidden(): Boolean {
        return true
    }

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        val obj = didOutputMetadataObjects.firstOrNull()
        val metadataObject = (obj as? AVMetadataMachineReadableCodeObject) ?: return
        val qrCode = metadataObject.stringValue ?: return
        onScan.invoke(qrCode) {
            UINotificationFeedbackGenerator().notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeSuccess)
            closeDialog()
        }
    }
}