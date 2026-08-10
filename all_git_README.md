# Git Cheat Sheet

A quick reference guide for managing local branches, switching contexts, deleting branches, and stashing changes.

---

## Useful Variations
*   `git branch`: Lists local branches.
*   `git branch -v`: Lists local branches with their last commit message.
*   `git branch -vv`: Lists local branches and shows which remote branches they track.
*   `git branch -a`: Lists all branches, including both local and remote-tracking ones.
*   `git branch --show-current`: Displays only the name of your current active branch.

## How to Switch Branches
*   `git switch <branch-name>`: The modern, recommended way to change branches.
*   `git checkout <branch-name>`: The traditional command used to switch branches.

## Deleting a Local Branch
> **Note:** Git will not let you delete the branch you are currently standing on. Switch away first.

*   `git checkout main`: Switch away from the branch you want to delete.
*   `git branch -d <branch-name>`: Deletes the branch safely. It will warn you if the branch has unmerged work.
*   `git branch -D <branch-name>`: Forces deletion. Use this if you want to discard all unmerged changes permanently.
*   `git fetch --prune`: Removes stale remote-tracking references from your local list.

---

## Git Stash
`git stash` temporarily shelves (stores) uncommitted changes so you can work on something else, without losing your current progress. It reverts your working directory to match the `HEAD` commit.

### Essential Stash Commands
*   `git stash`: Saves all tracked, modified files to a new stash.
*   `git stash save "message"`: Saves your changes with a custom descriptive note.
*   `git stash list`: Displays all your saved stashes with their unique index numbers.
*   `git stash apply`: Re-applies the most recent stash without deleting it from the stash list.
*   `git stash pop`: Re-applies the most recent stash and permanently removes it from the list.
*   `git stash drop`: Permanently deletes the most recent stash from your history.
*   `git stash clear`: Deletes all saved stashes across the entire repository.

### Handling Specific Scenarios
*   `git stash -u`: Stashes both tracked modifications and brand new, untracked files.
*   `git stash apply stash@{2}`: Applies a specific older stash by referencing its index number from the list.
*   `git stash branch <new-branch-name>`: Creates a new branch, checks it out, and pops your stashed changes into it.

