# ─────────────────────────────────────────────
#  STAGE 1: Build Angular con Node
# ─────────────────────────────────────────────
FROM node:20-alpine AS builder

WORKDIR /app

# Instalar dependencias primero (cacheo de capas)
COPY package.json package-lock.json ./
RUN npm ci --legacy-peer-deps

# Copiar código fuente y compilar en modo producción
COPY . .
RUN npm run build

# ─────────────────────────────────────────────
#  STAGE 2: Servir con Nginx
# ─────────────────────────────────────────────
FROM nginx:1.27-alpine

# Eliminar config por defecto
RUN rm /etc/nginx/conf.d/default.conf

# Copiar la configuración personalizada para SPA
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copiar artefactos de build (Angular 17+ genera en dist/browser/)
COPY --from=builder /app/dist/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
