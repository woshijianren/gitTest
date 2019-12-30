# 默认的登录行为
## 直接访问登录地址
1. 登录成功,直接跳到项目的根地址:也就是/
2. 登录失败,跳到login地址带上?errror

## 访问某一个地址(还没有登录的情况)
1. 登录成功,跳回你刚刚访问的地址
2. 登录失败,跳到login地址带上?errror


默认的登录处理地址与登录页面显示的地址都是/login,只不过区别一个是get,
一个是post请求

# login(登录)
有以下几个点
1. 自定义登录页面+ 登录处理url(处理是spring security处理)
2. 登录成功的url
3. 登录失败的url
4. 登录成功处理器
5. 登录失败的处理器

默认的成功与失败处理器是重定向的行为

## 获取登录之后的用户信息
1. SecurityContextHolder
2. Authentication
3. request.getUserPrincipal()

# logout(登出)

只需要配置logout即可,默认的行为是会删除会话数据
可以配置的项,见案例
# UserDetailService(自定义提取用户数据)
实现这个接口,然后利用auth.userDetailService(传进来)即可使用


# 作业1:
普通的一个mvc项目,完成自定义登录与登出
1.要求输入用户名的控件的名字是uname,其它保留默认
2.登录成功之后,往session中放入一个登录成功的时间

3.登出之后不能读取到放到会话中的登录时间
4.真正读取数据库

# 作业2:
做一个前后端分离的作业1