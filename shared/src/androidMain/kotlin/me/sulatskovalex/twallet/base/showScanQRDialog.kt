package me.sulatskovalex.twallet.base

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import me.sulatskovalex.twallet.common.Res
import me.sulatskovalex.twallet.providers.appColors
import me.sulatskovalex.twallet.providers.displaySize
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import kotlin.math.roundToInt

actual fun ModalController.showScanQRDialog(onScan: (scanned: String, onClose: () -> Unit) -> Unit) =
    present(ModalSheetConfiguration(cornerRadius = 8)) { key ->
        Column(Modifier.fillMaxSize().background(appColors.surface)) {
            BottomSheetHeader { popBackStack(key) }
            Spacer(Modifier.height(16.dp))
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current
            val isCameraEnabled = remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }
            val cameraProviderFeature = remember { ProcessCameraProvider.getInstance(context) }
            val permission = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { granted ->
                isCameraEnabled.value = granted
            }
            LaunchedEffect(Unit) {
                permission.launch(Manifest.permission.CAMERA)
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val boxSizeDp = displaySize.widthDp / 1.3f
                val boxSize = boxSizeDp * LocalDensity.current.density
                if (isCameraEnabled.value) {
                    val previewSizeDp: Dp = (displaySize.widthDp.dp - 56.dp)
                    val previewSizePx =
                        (previewSizeDp.value * LocalDensity.current.density).roundToInt()
                    AndroidView(
                        factory = { context ->
                            val previewView = PreviewView(context)
                            val preview = Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setResolutionSelector(
                                    ResolutionSelector.Builder()
                                        .setResolutionStrategy(
                                            ResolutionStrategy(
                                                android.util.Size(previewSizePx, previewSizePx),
                                                ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER,
                                            ),
                                        ).build()
                                )
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                QRCode { scanned ->
                                    onScan.invoke(scanned) {
                                        popBackStack(key)
                                    }
                                }
                            )
                            try {
                                cameraProviderFeature.get()
                                    .bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview,
                                        imageAnalysis
                                    )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(Res.image.ic_scan_qr),
                        contentDescription = "",
                        modifier = Modifier.size(boxSizeDp.dp).align(Alignment.Center),
                    )
                }

                Box(
                    Modifier.fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Transparent,
                                topLeft = Offset(
                                    size.width / 2 - boxSize / 2,
                                    size.height / 2 - boxSize / 2,
                                ),
                                size = Size(boxSize, boxSize),
                                cornerRadius = CornerRadius(16.dp.value),
                                style = Fill,
                                alpha = 0.5f,
                                colorFilter = ColorFilter.tint(Color.Transparent),
                                blendMode = BlendMode.Clear,
                            )
                        }
                )
            }
        }
    }
