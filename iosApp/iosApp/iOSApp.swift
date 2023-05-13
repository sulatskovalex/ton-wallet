import SwiftUI
import shared

@main
struct iOSApp: App {
    private let diManager = TWalletDI.shared

    init() {
        diManager.startDI()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
