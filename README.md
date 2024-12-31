# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)


 <!-- 🎉 🌟 👏  🍀 -->

##  🎉 环 境  

node v20.17.0

ollama 3.1

[https://ollama.com/download](https://ollama.com/download)

- 使用

https://www.npmjs.com/package/ollama
https://juejin.cn/post/7381478389468872741

## 🎉 codespaces 使用

https://docs.github.com/zh/enterprise-cloud@latest/codespaces  
https://juejin.cn/post/7102620860720087053  

## 🎉 构建镜像 

docker-compose up --build 

ollama 需要执行 
ollama run llama3.2 

```
@gonggbb ➜ /workspaces/antx-ollama (main) $ docker exec -it 77c /bin/bash
root@77c497b4fb17:/app# curl http://ollama:11434/v1/models
{"object":"list","data":[{"id":"llama3.2:latest","object":"model","created":1735625441,"owned_by":"library"}]}
root@77c497b4fb17:/app#

```
## ollama 3.1 太大, codespaces资源有限切换3.2

https://redesigned-adventure-6746p57pwxc4467-3000.app.github.dev/ 

##  存在的问题 

### 组件 App.js  未拆分  
 messagesMap不是同步,打印会没有数据, 需要useEffect 有点不合理 
 
### 组件 Independent.js 拆分的  
 切换 messagesMap 清空无效

### dokcer antx-o
 (blocked:mixed-content)
 


