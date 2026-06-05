```markdown
# TagMo Development Patterns

> Auto-generated skill from repository analysis

## Overview
This skill teaches you how to contribute effectively to the TagMo project, an Android application for managing Amiibo data. You'll learn the project's coding conventions, file organization, and the main development workflows, including updating the Amiibo database, adding features with UI changes, managing versions, updating documentation, handling build dependencies, and merging external contributions.

## Coding Conventions

### File Naming
- **Source files:** Use `snake_case` for file names.
  - Example: `amiibo_dictionary.java`
- **Resource files:** Use `snake_case` for XML layouts and values.
  - Example: `main_activity.xml`

### Imports
- **Style:** Use relative imports within the package.
  - Example:
    ```kotlin
    import com.hiddenramblings.tagmo.amiibo_dictionary
    ```

### Exports
- **Style:** Use named exports for classes and functions.
  - Example:
    ```kotlin
    class AmiiboDictionary { ... }
    ```

### Commit Messages
- **Pattern:** Freeform, often prefixed with `added` or `[DEV]`.
  - Example: `added new Amiibo entry for Splatoon 3`
- **Average Length:** ~41 characters.

## Workflows

### Update Database Entries
**Trigger:** When adding or updating Amiibo figures in the internal database.  
**Command:** `/update-database`

1. Open `app/src/main/java/com/hiddenramblings/tagmo/AmiiboDictionary.java`.
2. Add new entries or update existing ones as needed.
3. Save and commit your changes.
4. (Optional) Use a commit message like: `added new Amiibo entry for Zelda`

#### Example:
```kotlin
// Add a new Amiibo entry
amiiboList.add(Amiibo("Zelda", "0x00000000", ...))
```

---

### Feature Development with UI
**Trigger:** When implementing a new feature that involves both backend logic and UI changes.  
**Command:** `/new-feature-ui`

1. Create or modify Java/Kotlin files in `app/src/main/java/com/hiddenramblings/tagmo/`.
2. Update or add corresponding layout XML files in `app/src/main/res/layout/`.
3. Update strings or menu XMLs in `app/src/main/res/values/` or `app/src/main/res/menu/`.
4. Test the feature in the app.
5. Commit your changes with a descriptive message.

#### Example:
```kotlin
// In MainActivity.kt
fun onNewFeatureClicked() {
    // Implement feature logic here
}
```
```xml
<!-- In res/layout/activity_main.xml -->
<Button
    android:id="@+id/new_feature_button"
    android:text="@string/new_feature" />
```

---

### Version Bump
**Trigger:** When releasing a new version or after significant updates.  
**Command:** `/bump-version`

1. Open `app/build.gradle`.
2. Update the `versionCode` and `versionName` fields.
3. Save and commit the changes.

#### Example:
```groovy
versionCode 42
versionName "2.1.0"
```

---

### Readme Update
**Trigger:** When documenting new features, updating instructions, or adding credits.  
**Command:** `/update-readme`

1. Open `README.md`.
2. Add or update information as needed.
3. Save and commit the changes.

---

### Dependency or Build System Update
**Trigger:** When updating Gradle, NDK build files, or fixing build issues.  
**Command:** `/update-build-system`

1. Edit any of the following as needed:
    - `app/build.gradle`
    - `build.gradle`
    - `gradle/wrapper/gradle-wrapper.properties`
    - `ndk/jni/Android.mk`
2. Save and commit your changes.

---

### Merge External Contributions
**Trigger:** When integrating pull requests or upstream changes, resolving conflicts as needed.  
**Command:** `/merge-pr`

1. Merge the branch or pull request.
2. Resolve any conflicts in:
    - `.gitignore`
    - `app/build.gradle`
    - `app/src/main/java/com/hiddenramblings/tagmo/*.java`
    - `app/src/main/res/**/*`
    - `ndk/jni/**/*`
    - `README.md`
3. Test the merged code.
4. Commit the merge.

---

## Testing Patterns

- **Framework:** Unknown (not explicitly detected).
- **File pattern:** Test files are named with `.test.` in the filename.
  - Example: `amiibo_dictionary.test.kt`
- **Best Practice:** Place test files alongside the code they test or in a dedicated test directory.

---

## Commands

| Command             | Purpose                                                         |
|---------------------|-----------------------------------------------------------------|
| /update-database    | Add or update Amiibo entries in the internal database           |
| /new-feature-ui     | Implement a new feature with backend and UI changes             |
| /bump-version       | Update the app version in build.gradle                          |
| /update-readme      | Update documentation, instructions, or credits in README.md     |
| /update-build-system| Update Gradle/NDK build files or build dependencies             |
| /merge-pr           | Merge external contributions or upstream changes                |
```
