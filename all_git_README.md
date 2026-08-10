# Ultimate Git Cheat Sheet

A comprehensive reference guide for managing local branches, undoing changes, syncing remote repositories, and handling workflows.

---

## Branch Management

### Useful Variations
*   `git branch`: Lists local branches.
*   `git branch -v`: Lists local branches with their last commit message.
*   `git branch -vv`: Lists local branches and shows which remote branches they track.
*   `git branch -a`: Lists all branches, including both local and remote-tracking ones.
*   `git branch --show-current`: Displays only the name of your current active branch.

### How to Switch Branches
*   `git switch <branch-name>`: The modern, recommended way to change branches.
*   `git checkout <branch-name>`: The traditional command used to switch branches.

### Deleting a Local Branch
> **Note:** Git will not let you delete the branch you are currently standing on. Switch away first.

*   `git checkout main`: Switch away from the branch you want to delete first.
*   `git branch -d <branch-name>`: Deletes the branch safely. It will warn you if the branch has unmerged work.
*   `git branch -D <branch-name>`: Forces deletion. Use this if you want to discard all unmerged changes permanently.
*   `git fetch --prune`: Removes stale remote-tracking references from your local list.

---

## Context Switching & Stashing

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

---

## Undoing & Modifying History

### Git Reset
`git reset` is a powerful command used to undo changes, unstage files, or move your current branch pointer to a specific commit. It operates on three different trees: your Commit History (HEAD), the Staging Area (Index), and your Working Directory.

#### The Three Core Modes
*   `git reset --soft <commit>`: Moves the HEAD pointer to the specified commit. Your staging area and working directory remain completely untouched (your uncommitted changes are kept safe).
*   `git reset --mixed <commit>`: The default mode. Moves HEAD and resets the staging area to match it. Your working directory stays untouched, leaving files unstaged.
*   `git reset --hard <commit>`: Destroys everything. Moves HEAD, resets the staging area, and overwrites your working directory. Any uncommitted changes will be permanently lost.

#### Common Real-World Scenarios
*   `git reset HEAD <file>`: Unstages a specific file you accidentally added with `git add`, keeping your local modifications safe.
*   `git reset HEAD~1`: Undoes the very last commit but keeps all the changes from that commit in your working directory so you can edit and try again.
*   `git reset --hard HEAD`: Completely wipes out all uncommitted local modifications in your working directory to give you a clean slate.
*   `git reset --hard origin/main`: Forces your local branch to exactly match the remote tracking branch, throwing away any local commits or changes.

### Git Revert
`git revert` creates a brand new commit that does the exact opposite of an existing commit. Unlike `git reset` (which alters your project history), `git revert` is completely safe for public branches because it leaves existing history intact and only moves the project forward.

#### Essential Commands
*   `git revert <commit-hash>`: Creates a new commit that rolls back the changes made by the specified commit.
*   `git revert HEAD`: Automatically reverts the very last commit made on your current branch.
*   `git revert HEAD~3`: Reverts the commit that happened exactly three steps back in your history.
*   `git revert -n <commit-hash>`: Modifies your workspace with the inverse changes but does not automatically commit. This lets you inspect or group multiple reverts.

---

## Integrating Changes

`git merge` and `git rebase` are the two primary ways to integrate changes from one Git branch into another. While they achieve the same ultimate goal, they handle your project's commit history completely differently.

### Git Merge: Combining Histories
*   `git merge <branch-name>`: Integrates target branch updates into your active branch.
*   **Merge Commits**: Creates a unique, dedicated commit linking both historical paths together.
*   **Traceable History**: Preserves exact chronological timelines showing exactly when features joined the branch.
*   **Non-Destructive**: Leaves your existing commits and their unique cryptographic hashes unchanged.

### Git Rebase: Rewriting History
*   `git rebase <branch-name>`: Plucks your local commits and re-applies them on top of the target branch's head.
*   **Linear Timeline**: Eliminates cluttering merge commits, leaving a perfectly flat project history line.
*   **Alters History**: Rewrites repository history by generating brand-new commit hashes for your work.
*   **Safety Warning**: Never rebase commits that you have already pushed to a shared public branch.

---

## Syncing with Remote Repositories

`git clone` and `git pull` are both used to download files from a remote repository (like GitHub or GitLab), but they are used at completely different stages of your project. You use `git clone` once to download a project for the first time, and you use `git pull` regularly to download updates after that.

### Git Clone: Getting the Project First
*   `git clone <repository-url>`: Downloads the remote repository files and configures local tracking variables.

### Git Pull: Fetching Ongoing Updates
*   `git pull`: Fetches updates from the tracked remote branch and immediately merges them into your active workspace.

---

## Advanced Remote Workflows

`git fetch` and `git checkout` are used together to get remote updates and switch your workspace to view or work on them. Running `git fetch` downloads the latest information from GitHub or GitLab, while `git checkout` actually changes the files in your working directory.

### Git Fetch: Reviewing Remote Changes
`git fetch` downloads all the latest commits, files, and branches from the remote repository to your local machine, but it does not touch or modify your current workspace. It updates your local tracking markers (like `origin/main`).

*   `git fetch origin`: Downloads all new data from the default remote server.
*   `git fetch --all`: Downloads changes from every tracked remote repository.

### Git Checkout: Switching Your Viewpoint
`git checkout` updates the files in your working directory to match a specific branch or commit, allowing you to start working on that version of the code.

*   `git checkout <branch-name>`: Switches your view to an existing local branch.
*   `git checkout -b <new-branch-name>`: Creates a brand-new local branch and switches you to it immediately.
*   `git checkout <commit-hash>`: Moves your workspace to a specific point in time (puts you in a "detached HEAD" state).
*   `git checkout -- <file-name>`: Discards local uncommitted changes in a specific file, reverting it back to the last commit.
### Essential Cherry-Pick Commands
* **`git cherry-pick <commit-hash>`**: Applies the changes from a single, specific commit onto your current active branch.
* **`git cherry-pick <commit-A> <commit-B>`**: Applies multiple specific, separate commits in one command.
* **`git cherry-pick <commit-A>..<commit-B>`**: Applies a continuous range of commits from Commit A up to Commit B (Commit A is excluded).
* **`git cherry-pick <commit-A>^..<commit-B>`**: Applies a continuous range of commits, including Commit A all the way through Commit B.
* **`git cherry-pick -n <commit-hash>`**: Applies the changes to your working directory and staging area, but does not create the commit automatically. This lets you inspect or modify the changes first.

### How to Handle Cherry-Pick Conflicts
If the code changes clash with your current branch, Git will pause and ask you to fix them. Use these commands to manage the pause state:
* **`git cherry-pick --continue`**: Resumes the process after you manually resolve the code conflicts and stage the files (`git add`).
* **`git cherry-pick --skip`**: Skips the current conflicting commit entirely and moves on to the next one in your queue.
* **`git cherry-pick --abort`**: Cancels the entire operation completely and returns your branch to exactly how it looked before you started.

---

### Terminal Shell Redirections
The command `echo " devops" >> devops` is a standard terminal shell command (not a Git command) used to append text to a file.

* **`>>` (Append)**: Adds text to the end of the file safely.
* **`>` (Overwrite)**: Wipes out the entire file and replaces it with the new text.

---

### Deployment Manifest Template
You can generate your Kubernetes configuration using this block:

```bash
cat << 'EOF' > deployment.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app-deployment
  labels:
    app: my-app
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
      - name: my-app-container
        image: nginx:latest
        ports:
        - containerPort: 80
EOF
\`\`\`
EOF
