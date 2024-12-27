#!/bin/bash

# 启动 Node.js 应用
echo "启动 Node.js 应用..."
npm start

set -ex

# 启动 Ollama 服务
echo "启动 Ollama 服务..."
ollama serve &

# 等待服务启动（根据需要调整时间）
sleep 5

# 加载指定模型
echo "加载模型 llama3.2..."
ollama run llama3.2 &

# 等待模型加载完成（根据需要调整时间）
sleep 5


