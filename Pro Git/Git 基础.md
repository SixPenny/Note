## 获取Git 仓库

本地初始化一个仓库 `git init`  
从远端克隆一个 `git clone repo-url [本地仓库名称]`

## 更新到仓库

目录下的文件都有两种状态：未追踪和已追踪。已追踪表示文件已经被纳入了版本控制。

![](https://note.youdao.com/yws/public/resource/2b1726cb603247fa722efddfdc31eff1/xmlnote/5AC4CB33A728416D9B649CE5967A47D5/27270)


对应的命令分别是：
```git
git add file-path
git stage file-path
git commit [-m comments]
git rm file-path
```

[git add 与 stage 的区别](https://stackoverflow.com/questions/34175799/differencies-between-git-add-and-git-stage-command)

## 状态查看

`git status [-s]`

## 忽略文件
文件 .gitignore 的格式规范如下:
- 所有空行或者以` # `开头的行都会被 Git 忽略。 • 可以使用标准的 glob 模式匹配。
- 匹配模式可以以(`/`)开头防止递归。
- 匹配模式可以以(`/`)结尾指定目录。
- 要忽略指定模式以外的文件或目录，可以在模式前加上惊叹号(`!`)取反。

```txt
# no .a files
*.a

# but do track lib.a, even though you're ignoring .a files above
!lib.a

# only ignore the TODO file in the current directory, not subdir/TODO
/TODO

# ignore all files in the build/ directory
build/

# ignore doc/notes.txt, but not doc/server/arch.txt
doc/*.txt

# ignore all .pdf files in the doc/ directory
doc/**/*.pdf
 
```

## 查看修改

```txt
// 比较工作目录与暂存区快照的区别
git diff

// 已暂存区域和仓库的比较
git diff --staged
```

使用 `git difftool` 来使用其他工具来查看修改差异

```txt
//查看系统支持的diff 工具
 git difftool --tool-help

//设置diff 使用的tool
git config --global diff.tool vimdiff

// 查看差异
git difftool [--staged]
```

## 提交更新

`git commit` 会打开文本编辑器让你输入提交信息，所有注释掉的内容在退出之后都被丢弃，注释只包含 `git status` 的输出，如果需要更详细的修改内容提示，可以使用 `-v` 选项


`git commit -a` 可以跳过 stage 步骤，将所有已跟踪的文件暂存起来并提交（这样就不能选择此次提交的文件了）

## 移除文件

`git rm file-pattern` 会将文件从暂存区移除， 然后提交，工作目录中的对应也会被删除。

手动运行 `rm ` 命令只会删除工作目录中的文件，然后在运行 `git rm` 命令来删除git 管理的文件。

如果删除时文件已经修改过并已放到暂存区域的话，需要使用 `-f` 选项来强制删除

如果只想删除暂存区的文件，但是保存工作目录中的文件，那么需要使用 `git rm --cached file-pattern`（当你忘记添加 .gitignore 文件，不小 心把一个很大的日志文件或一堆 .a 这样的编译生成文件添加到暂存区时，这一做法尤其有用）

```txt
// 移除所有log 文件，* 在shell中有特殊含义，需要转义
git rm --cached log/\*.log

// 删除所有 ～ 结尾的文件
git rm \*~
```

## 移动文件

Git 不会显式跟踪文件移动操作，要对文件改名，可以执行 `git mv file_from file_to` , 但实际上，`git mv` 相当于运行了三条命令：

```txt
mv file_from file_to
git rm file_from
git add file_to
```
这个在 `reset` 时可以看出来，`mv` 操作并不能一次 reset 回来，需要 `reset` 后在`checkout` 出来，并且把改名后的文件删除

## 查看提交历史 

`git log` 会按提交时间返回提交信息

`-p` 用于显示每次提交的内容差异， `-2` 仅显示最近的2次提交  
如果想看提交的简略信息，可以使用`--stat` 选项  
`--pretty=?` 可以指定输出的格式， `--pretty=oneline`在一行内输出, `--pretty=format:"%h - %an, %ar : %s"` 可以定制输出的格式，可用格式如下：

`format` 可用格式：

| 选项 | 说明 |
|---|---|
| %H | 提交对象(commit)的完整哈希字串 |
| %h | 提交对象的简短哈希字串|
| %T | 树对象(tree)的完整哈希字串|
| %t | 树对象的简短哈希字串|
| %P | 父对象(parent)的完整哈希字串|
| %p | 父对象的简短哈希字串|
| %an | 作者(author)的名字|
| %ae | 作者的电子邮件地址|
| %ad | 作者修订日期(可以用 --date= 选项定制格式)|
| %ar |  作者修订日期，按多久以前的方式显示|
| %cn | 提交者(committer)的名字|
| %ce | 提交者的电子邮件地址|
| %cd | 提交日期|
| %cr | 提交日期，按多久以前的方式显示|
| %s | 提交说明|


`git log` 常用选项有：

| 选项 | 说明
| --- | --- | 
| -p | 按补丁格式显示每个更新之间的差异。|
| --stat | 显示每次更新的文件修改统计信息。|
| --shortstat | 只显示 --stat 中最后的行数修改添加移除统计。|
| --name-only | 仅在提交信息后显示已修改的文件清单。|
| --name-status | 显示新增、修改、删除的文件清单。|
| --abbrev-commit | 仅显示 SHA-1 的前几个字符，而非所有的 40 个字符。|
| --relative-date | 使用较短的相对时间显示(比如，“2 weeks ago”)。|
| --graph | 显示 ASCII 图形表示的分支合并历史。
| --pretty | 使用其他格式显示历史提交信息。可用的选项包括 oneline，short，full，fuller 和  format(后跟指定格式)。|


`git log` 默认是显示最新的几条，我们可以使用选项来搜索来列出符合条件的提交
| 选项 | 说明|
| --- | --- | 
| -<n> | 只显示前 n 条提交 |
| --since, --after | 仅显示指定时间之后的提交。可以是某一天 2019-09-10, 或相对时间 `2 weeks agos` | 
|  --until, --before | 仅显示指定时间之前的提交。|
| --author | 仅显示指定作者相关的提交。|
| --committer | 仅显示指定提交者相关的提交。|
| --grep | 仅显示含指定关键字的提交
| -S | 仅显示添加或移除了某个关键字的提交|


`git log` 的还有 n 多选项，可以`git help log` 查看

## 撤销操作

> 注意：有些撤销操作不可逆，可能因为操作失误而导致之前的工作丢失

漏了文件没有提交，但是又不想提交一次新的，可以使用 `git commit --amend`命令，这次提交会覆盖上一次提交

取消暂存的文件，`git reset HEAD <file>...`  
撤销工作目录中对文件的修改 `git checkout --  <file>`  


## 远程仓库的使用

查看远程仓库 `git remote [-v]` 

添加远程仓库 `git remote add <shortname> <url>`

从远程仓库中拉取 `git fetch <remote-name>` , fetch 只会拉取数据到本地仓库，但并不会自动合并并修改你当前工作，你必须手动合并并提交到工作目录中。 如果你有一个分支设置为跟踪一个远程分支，可以使用git pull命 令来自动的抓取然后合并远程分支到当前分支。

推送到远程仓库 `git push [remote-name] [branch- name]`  (只有当你有所克隆服务器的写入权限，并且之前没有人推送过时，这条命令才能生效。)

查看某个远程仓库 `git remote show <remote-name>`  

远程仓库到重命名 `git remote rename <name-now> <name-changed>`, 同样也会修改你的远程分支名称

移除远程仓库 `git remote rm <name>`  

## 打标签

Git 可以给历史中的某一个提交打上标签，以示重要

列出已有标签 `git tag`

用特定模式来搜索标签 `git tag -l 'v1.1*'`

git 标签分为 轻量标签（lightweight）与附注标签（annotated） 两种，轻量标签只是对一个特定提交的引用，而附注标签则是存储在 Git 数据库中的一个完整对象，是可以被校验的，包含打标签者的名字、邮件、日期和标签信息，

创建附注标签 `git tag -a v1.4 -m 'tag description' `, 创建轻量标签直接写标签名字即可`git tag v1.5-lw` 
后期打标签，可以添加标签时指定某次提交的校验和 `git tag -a v1.6 <checksum-commit>` 

标签默认不会推送到服务器上，必须显式地推送标签到服务器上， `git push origin <tag-name>` ,要推送多个标签，可以使用`git push orgin --tags`

删除本地标签 `git tag -d <tag-name>`，删除远程服务器上的标签需使用 `git push origin :refs/tags/<tag-name>`


## Git 别名

```
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status

// 取消暂存别名
git config --global alias.unstage 'reset HEAD --'

//可以在命令前加 ! 来执行外部命令
git config --global alias.visual '!gitk'
```


