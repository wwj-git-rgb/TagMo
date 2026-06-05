---
name: version-bump
description: Workflow command scaffold for version-bump in TagMo.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /version-bump

Use this workflow when working on **version-bump** in `TagMo`.

## Goal

Updates the app version in build.gradle after a release or significant change.

## Common Files

- `app/build.gradle`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Edit app/build.gradle to update the version number.

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.