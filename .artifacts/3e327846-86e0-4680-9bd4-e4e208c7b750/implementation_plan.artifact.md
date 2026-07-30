# Koin Compose Integration Plan

Integrate Koin dependency injection into the project and set up basic injection for the `DialScreenViewModel`.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///D:/studio_progects/maksec_test/gradle/libs.versions.toml)
- Add Koin versions and library definitions.

#### [MODIFY] [build.gradle.kts (app)](file:///D:/studio_progects/maksec_test/app/build.gradle.kts)
- Add Koin dependencies to the app module.

---

### Dependency Injection Setup

#### [NEW] [MaksecApplication.kt](file:///D:/studio_progects/maksec_test/app/src/main/java/com/maksec/test/MaksecApplication.kt)
- Create a custom `Application` class that starts Koin.

#### [MODIFY] [AndroidManifest.xml](file:///D:/studio_progects/maksec_test/app/src/main/AndroidManifest.xml)
- Register `MaksecApplication` in the `<application>` tag.

#### [NEW] [AppModule.kt](file:///D:/studio_progects/maksec_test/app/src/main/java/com/maksec/test/di/AppModule.kt)
- Define a Koin module to provide `DialScreenViewModel`.

---

### UI Integration

#### [MODIFY] [DialScreen.kt](file:///D:/studio_progects/maksec_test/app/src/main/java/com/maksec/test/screen/dial_screen/DialScreen.kt)
- Update `DialScreenRoot` to use Koin's `koinViewModel()` instead of `viewModel()`.

## Verification Plan

### Automated Tests
- Run `./gradlew app:assembleDebug` to ensure the project builds successfully with the new dependencies.

### Manual Verification
- Deploy the app to a device and ensure it starts without crashing.
- Verify that `DialScreen` correctly receives the `DialScreenViewModel` instance via Koin.
