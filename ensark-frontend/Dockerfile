# ==============================================================================
# Build Stage
# ==============================================================================
FROM node:20-alpine AS builder

WORKDIR /app

# Copy package descriptors and install dependencies
COPY package.json package-lock.json ./
RUN npm ci

# Copy source code and build production bundle
COPY . .
RUN npm run build -- --configuration production

# ==============================================================================
# Runtime Stage
# ==============================================================================
FROM nginx:alpine

# Default port for local / container environment
ENV PORT=80

# Copy Nginx template for dynamic port substitution (Railway & local support)
COPY default.conf.template /etc/nginx/templates/default.conf.template

# Copy built Angular static assets to Nginx web root
COPY --from=builder /app/dist/ensark-frontend/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
