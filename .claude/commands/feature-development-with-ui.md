---
name: feature-development-with-ui
description: Workflow command scaffold for feature-development-with-ui in TagMo.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /feature-development-with-ui

Use this workflow when working on **feature-development-with-ui** in `TagMo`.

## Goal

Implements a new feature that includes both Java logic and UI changes.

## Common Files

- `app/src/main/java/com/hiddenramblings/tagmo/*.java`
- `app/src/main/res/layout/*.xml`
- `app/src/main/res/values/*.xml`
- `app/src/main/res/menu/*.xml`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Create or modify Java files in app/src/main/java/com/hiddenramblings/tagmo/
- Update or add corresponding layout XML files in app/src/main/res/layout/
- Update strings or menu XMLs in app/src/main/res/values/ or app/src/main/res/menu/

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.