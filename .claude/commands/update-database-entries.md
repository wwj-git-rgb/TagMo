---
name: update-database-entries
description: Workflow command scaffold for update-database-entries in TagMo.
allowed_tools: ["Bash", "Read", "Write", "Grep", "Glob"]
---

# /update-database-entries

Use this workflow when working on **update-database-entries** in `TagMo`.

## Goal

Adds or updates Amiibo entries in the internal database.

## Common Files

- `app/src/main/java/com/hiddenramblings/tagmo/AmiiboDictionary.java`

## Suggested Sequence

1. Understand the current state and failure mode before editing.
2. Make the smallest coherent change that satisfies the workflow goal.
3. Run the most relevant verification for touched files.
4. Summarize what changed and what still needs review.

## Typical Commit Signals

- Edit app/src/main/java/com/hiddenramblings/tagmo/AmiiboDictionary.java to add or update entries.

## Notes

- Treat this as a scaffold, not a hard-coded script.
- Update the command if the workflow evolves materially.