Git 的分支非常轻量，并且 Git 鼓励在工作流中频繁的使用分支和合并。

## 分支简介

Git 的分支，其实本质上仅仅是指向提交对象的可变指针。

分支创建 `git branch <branch-name>`, 只会创建分支而不会切换到分支上，再使用 `git checkout <branch-name>` 来切换分支，Git 如何知道当前是在那个分支上呢？Git 有一个 `HEAD` 指针，指向当前所在本地分支，分支切换就只是将 `HEAD` 指针指向 checkout 指定到分支，因此速度很多而且和项目规模没有关系。

查看分支当前所指向到对象，使用 `git log --decorate` 命令


## 分支的新建与合并

可以使用 `git checkout -b <branch-name>` 直接新建分支并切换到此分支

分支开发完毕，切换回master `git checkout master`, 然后将分支代码合并到master `git merge <branch-name>` 

合并回去之后，分支不用了可以删除，`git branch -d <branch-name>`， 如果分支没有合并过，该命令会报错通知你工作还没有merge， 可以使用 `git branch -D <branch-name>` 来强制删除( `-D 相当于 -d -f`, 命令选项很多大些字母就相当于小写+ `-f`)

分支的合并分为 fast-forward 和 recursive merge 两种， fast-forward 就只是将指针向前移动，recursive merge 是将两个分支的指针和分支的共同祖先做三方合并，然后提交，这个合并提交会有两个祖先(Git 会自行决定选取哪一个提交作为最优的共同祖先，并以此作为合并的基础)

遇到冲突时，需要手动解决冲突，然后通过 `add` 通知 git 冲突已经解决了（即一旦暂存了这个文件，git 就会认为冲突已经解决了），然后 `git commit` 来提交这次合并提交


## 分支管理

`git branch` 列出所有的分支，`*` 代表当前分支  
`git branch -v` 来列出最后一次提交  
`git branch --merged` 列出所有的已合并的分支， `git branch --no-merged` 列出未合并的分支    


## 分支开发工作流

长期分支：master 上保留稳定的代码，develop 用于作为开发或测试的分支（不必完全稳定），稳定后合并到 master分支

特性(feature)分支: 是一种短期分支，用于实现单一特性或相关工作，bugfix 也是一种特性分支


## 远程分支

远程跟踪分支是远程分支状态的引用。 它们是你不能移动的本地引用，当你做任何网络通信操作时，它们会自 动移动。

远程分支以 `(remote)/(branch) `形式命名.

origin 是在 clone 时 git 自动赋予的名字，可以使用 `git clone -o <remote-name>` 来自行命名

同步工作，使用 `git fetch [origin]` 来拉取服务器上的数据，更新远程分支的指针，合并操作与本地分支一样 `git merge origin/master` ，`git pull` 操作是前两个操作的简化

推送本地分支到服务器 `git push origin <branch-name>[:<remote-branch-name>]`  

跟踪分支是与本地分支直接关联的远程分支，clone 之后本地分支会自动跟踪远程分支，`git pull` `git push` 命令会自动查找当前分支跟踪的远程分支

本地创建分支并跟踪远程分支， `git checkout -b [branch] [remotename]/[branch]` , 也可以使用 `git checkout --track [remotename]/[branch]`(这样本地分支会与远程分支的名称一样)

更改跟踪的远程分支， `git branch -u [remote/branch]` 

要查看所有跟踪的远程分支 `git branch -vv`

删除远程分支 `git push origin --delete <branch-name>` 

## 变基（rebase）

前面的合并会进行三方合并，生成一个合并提交。变基是将在分支上所做的修改，在另一个分支上重放，一般是为了确保在向远程分支上推送时保持历史记录的整洁。

`git rebase master` 将当前分支所做的修改重放到 master 上，并将当前分支的指针指向重放后的新指针。

使用 `git rebase [basebranch] [featurebranch] `命 令可以直接将特性分支变基到目标分支(basebranch)上。这样做能省去你先切换到 featurebranch 分支，再对其执行变基命令的多个步骤。

变基的使用准则： ***不要对在你的仓库外有副本的分支执行变基。***

如果 有人推送了经过变基的提交，并丢弃了你的本地开发所基于的一些提交， 可以用变基解决变基，即`git rebase origin/master` 将本地分支变基到远程分支上去。
