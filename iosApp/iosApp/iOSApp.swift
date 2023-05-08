import SwiftUI
import shared

@main
struct iOSApp: App {
    private let diManager = TWalletDI.shared

    init() {
        diManager.startDI()
    }

    deinit {
        diManager.stopDI()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}