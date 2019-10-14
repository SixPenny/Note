## 选择修订版本

1. ***完整的 sha-1*** 或唯一的sha-1 前缀就可以指定某一次提交
2. ***分支引用***，可以在任意一个 Git 命令中使用分支名来 代替对应的提交对象或者 SHA-1 值
3. ***引用日志***， 记录了最近几个月你的 HEAD 和分支引用所指向的历史。
4. ***祖先引用***，引用的尾部加上一个 `^`， Git 会将其解析为该引用的上一个提交. 在 `^` 加一个数字，代表上一个提交的第 n 父提交（合并提交有两个父引用，需要使用 `^2` 来引用第二个父提交）。 `~` 也可以指明祖先提交，单个`~`与 `^` 等价，但是 `~n` 代表向后回溯 n 个父提交，即 `HEAD~2` 代表父提交的父提交（等于`HEAD^^`）。可以一起使用 `HEAD~2^2` 代表祖父提交的第二个父提交
5. ***提交区间***
    1. **双点**， 选出在一个分支而不在另一个分支中的提交 `git log master..branch1` 表示显示在`branch1` 不在 `master` 中的提交，`git log origin/master..HEAD` 可以查看还没有推送到远端的提交（-）
    2. **多点**， 指定多个分支，在任意引用前加上`^` 或 `--not` 来指明不希望提交被包含的分支， 因此双点语法`git log b..master` 等价于 `git log master ^b` ,但是可以指定多个 `git log a b c --not d` 
    3. **三点**，选择出被两个引用中的一个包含但又不被两者同时包含的提交， 使用 `--left-right` 选项可以打印出每个提交属于哪个分支（xor）

## 储藏与清理

### 储藏

储藏会处理工作目录的脏的状态——即跟踪文件的修改与暂存的改动——然后将未完成的修改保存到一个栈上， 而你可以在任何时候重新应用这些改动

```
usage: git stash list [<options>]
   or: git stash show [<stash>]
   or: git stash drop [-q|--quiet] [<stash>]
   or: git stash ( pop | apply ) [--index] [-q|--quiet] [<stash>]
   or: git stash branch <branchname> [<stash>]
   or: git stash save [--patch] [-k|--[no-]keep-index] [-q|--quiet]
		      [-u|--include-untracked] [-a|--all] [<message>]
   or: git stash [push [--patch] [-k|--[no-]keep-index] [-q|--quiet]
		       [-u|--include-untracked] [-a|--all] [-m <message>]
		       [-- <pathspec>...]]
   or: git stash clear
```

想要切换分支或拉取代码，但是不想提交本地的修改，可以使用 `git stash` 将修改保存到栈上，操作完成后，可以使用 `git stash list` 列出所有的储藏，然后使用 `git stash apply` 来应用存储的修改（默认最新，也可以使用 list 中列出的 `stash@{n}` 指定需要应用的储藏）， 但是已经暂存的文件却不会重新暂存，使用`--index` 选项来回到最初的状态。

`apply` 之后栈上并不会移除这个储藏，可以使用 `drop` 来丢弃，也可以使用 `git stash pop` 来应用储藏后丢掉它

默认 stash 会将已暂存和未暂存的文件都推到栈上，可以使用 `git stash save --keep-index` 来只将未暂存的文件保存到栈上(保持已暂存的文件状态)

默认 stash 只会保存已跟踪的文件，可以使用 `git stash save --include-untrached | -u` 来保存已创建的未追踪文件

储藏在重新应用时可能会发生冲突，可以通过 `git stash branch <branch-name>` 新建一个 分支，检出储藏时所在的提交，并且重新应用储藏

### 清理

`git clean` 可以清理工作目录， `git clean -f -d` 可以删除所有未被追踪的文件和空目录，要看看移除了什么，运行`git clean -d -n` 来查看clean 会做什么。

默认情况下，git clean命令只会移除没有忽略的未跟踪文件，使用 `-x` 选项来移除即使被`.gitignore`忽略的文件

`-f` 会对所有的文件都会应用，`-i` 选项会交互地让你选择对每一个文件执行什么操作

## 搜索

### Git grep

```
NAME
       git-grep - Print lines matching a pattern
SYNOPSIS
       git grep [-a | --text] [-I] [--textconv] [-i | --ignore-case] [-w | --word-regexp]
                  [-v | --invert-match] [-h|-H] [--full-name]
                  [-E | --extended-regexp] [-G | --basic-regexp]
                  [-P | --perl-regexp]
                  [-F | --fixed-strings] [-n | --line-number] [--column]
                  [-l | --files-with-matches] [-L | --files-without-match]
                  [(-O | --open-files-in-pager) [<pager>]]
                  [-z | --null]
                  [ -o | --only-matching ] [-c | --count] [--all-match] [-q | --quiet]
                  [--max-depth <depth>] [--[no-]recursive]
                  [--color[=<when>] | --no-color]
                  [--break] [--heading] [-p | --show-function]
                  [-A <post-context>] [-B <pre-context>] [-C <context>]
                  [-W | --function-context]
                  [--threads <num>]
                  [-f <file>] [-e] <pattern>
                  [--and|--or|--not|(|)|-e <pattern>...]
                  [--recurse-submodules] [--parent-basename <basename>]
                  [ [--[no-]exclude-standard] [--cached | --no-index | --untracked] | <tree>...]
                  [--] [<pathspec>...]

s

```
grep 命令可以很方便地从提交历史或者工作目录中查找一个字符串或者正则表达式。

默认情况 grep 会搜索工作目录中的文件， `-n` 来显示文件的行号，如果向看匹配的行属于哪一个方法或函数，可以使用 `-p` 选项

使用 `--and` 标志来查看复杂的字符串组合(同一行同时包含多个匹配), 此时需要 `-e` 来指定搜索 pattern
```
查看在 旧版本 1.8.0 的 Git 代码库中  
定义了常量名包含 “LINK” 或者 “BUF_MAX”  
这两个字符串所在的行。

git grep --break --heading \
      -n -e '#define' --and \( -e LINK -e BUF_MAX \) v1.8.0
 
注：--or 是默认的操作符，括号里的两个pattern 是 or 关系
```

### Git 日志搜索

`git log` 相关选项可以搜索什么时候存在或引入的（查找diff 文件）。

 `-S` 选项来显示新增和删除字
符串的提交, `git log -SZLIB_BUF_MAX --oneline`。

行日志搜索，使用 `-L ` 选项展示代码中一行或者一个函数的历史
```
git log -L [start,end:file'|:funcname:file]


<start> and <end> can take one of these forms:

           o   number

               If <start> or <end> is a number, it specifies an absolute line number (lines count from 1).

           o   /regex/

               This form will use the first line matching the given POSIX regex. If <start> is a regex, it will search from the end of the previous -L range, if
               any, otherwise from the start of file. If <start> is "^/regex/", it will search from the start of file. If <end> is a regex, it will search
               starting at the line given by <start>.

           o   +offset or -offset

               This is only valid for <end> and will specify a number of lines before or after the line given by <start>.

           If ":<funcname>" is given in place of <start> and <end>, it is a regular expression that denotes the range from the first funcname line that matches
           <funcname>, up to the next funcname line. ":<funcname>" searches from the end of the previous -L range, if any, otherwise from the start of file.
           "^:<funcname>" searches from the start of file.

```

## 重写历史

修改最后一次提交 `git commit -amend` ，如果已经推送到服务器，就不要做这个操作

修改多个提交，Git 没有改变历史工具，但可以使用变基来变基一系列提交，`git rebase -i HEAD^2`, 交互式变基给你一个它将会运行的脚本。 它将会从你在命令行中指定的提交开始，从上到下的依次重演每一个提交引入的修改.
- 将 pick 改为 edit 表示你将要修改这个提交
- 删除某个提交可以完全删除此次提交的变化
- 改变提交的顺序，相当于真实改变了 log 中的提交顺序
- 压缩提交，将 pick 改为 squash， 可以将squash 指定的提交合并到父提交中，也就是变为了一个提交历史，但是修改依然是在的
- 拆分提交，通过 `git reset HEAD^` 做一次混合重置，然后提交几次后，再`git rebase --continue`

核武器级选项 `filter-branch`

- 从每一个提交中移除一个文件 `git filter-branch --tree-filter 'rm -f passwords.txt' HEAD` 
- 全局修改邮箱地址
```
git filter-branch --commit-filter '
          if [ "$GIT_AUTHOR_EMAIL" = "schacon@localhost" ];
          then
                  GIT_AUTHOR_NAME="Scott Chacon";
                  GIT_AUTHOR_EMAIL="schacon@example.com";
                  git commit-tree "$@";
else
fi' HEAD
```

## reset 命令

先认识一下三棵树

| 树 | 用途 |
| --- | ---| 
|HEAD | 上一次提交的快照，下一次提交的父节点 |
| index | 预期的下一次提交的快照(暂存区域) |
| working Directory | 沙盒 |

![](https://note.youdao.com/yws/public/resource/2b1726cb603247fa722efddfdc31eff1/xmlnote/032F3B7518AF4CD2A0DF88521CC47666/27668)

Git reset 以特定的顺序重写这三棵树

1. 向前移动 HEAD 指针的指向 （指定了--soft 选项，则执行到此）
2. 将 HEAD 指向的快照更新到 index 中 （--mixed 选项停止在此，默认行为) (***working directory 中的内容是不变的，`git status` 显示 unstaged 的内容是工作目录和 index 中的文件 diff 出来的***)
3. 使用索引中的内容覆盖工作目录(--hard 模式)，**HEAD 后的提交内容全部丢失**

### 通过路径来 reset

可以提供给 reset 一个路径，则 reset 会跳过第一步，并且将它的作用范围限定为指定的文件或文件集合。

运行 `git reset file.txt`(等同于 `git reset --mixed HEAD file.txt`), 所做的操作为：

1. 移动 HEAD 指针（跳过）
2. 索引更新为 head 指向的快照

所以本质上是将文件从index 拷贝到 working directory中， 然后将 HEAD 中的该文件复制到 index 中，它可以**取消暂存文件**

[How to undo 'git reset'?
](https://stackoverflow.com/questions/2510276/how-to-undo-git-reset)

> git reset 'HEAD@{1}'  
> git reflog view all ref updates, reset back


[what does git reset double dash do ](https://stackoverflow.com/questions/14217853/what-does-the-double-dash-option-do-on-git-reset)

> `--` separates branch names from file names, in case there is any ambiguity (if you have a branch and a file with the same name). If there are no ambiguities, you don't need the `--`.
> Also as mentioned by Jonas Wielicki, this allows for file names that start with a `-`; these would otherwise be interpreted as command-line options.

### 压缩提交

```
// 将 head 指针向前移动
git reset --soft head~2
 
// 重新提交
git commit 

这样就将 head 与 head~1 的提交压缩成了一个，内容不变
```

### checkout 比较

checkout 不带文件路径 `git checkout [<branch-name>]` 表示切换分支，指定文件路径时`git chekcout [commits] <file-path>`会将此次提交（commits）中的内容更新到索引，并且覆盖工作目录中的文件

