# 使用多阶段构建：下载并验证 Ollama 安装脚本
FROM alpine AS builder
RUN apk add --no-cache curl bash
WORKDIR /tmp

# 下载 Ollama 安装脚本
RUN set -ex \
    && echo "开始下载 Ollama 安装脚本..." \
    && curl -fsSL -o install.sh https://ollama.com/install.sh \
    && echo "Ollama 安装脚本下载完成." \
    && chmod +x install.sh

# 最终镜像：基于 Node.js 20
FROM node:20
WORKDIR /app

# 设置 Ollama 环境变量
ENV OLLAMA_CACHE_DIR="/usr/local/ollama/cache"
ENV OLLAMA_MODELS_DIR="/usr/local/ollama/models"
ENV PATH="$PATH:/usr/local/bin"

# 从 builder 阶段复制 Ollama 安装脚本
COPY --from=builder /tmp/install.sh /usr/local/bin/install-ollama.sh

# 安装 Ollama
RUN set -ex \
    && echo "开始安装 Ollama..." \
    && chmod +x /usr/local/bin/install-ollama.sh \
    && /usr/local/bin/install-ollama.sh \
    && echo "Ollama 安装完成." \
    && rm /usr/local/bin/install-ollama.sh \
    && mkdir -p $OLLAMA_CACHE_DIR $OLLAMA_MODELS_DIR

# 复制项目文件
COPY package*.json ./
RUN npm install --production
RUN ls 


COPY . .

COPY start.sh /app/start.sh
RUN ls -l /app
RUN chmod +x /app/start.sh

# 暴露服务端口
EXPOSE 3000 11434

# 使用自定义启动脚本作为容器入口
CMD ["bash", "/app/start.sh"]

