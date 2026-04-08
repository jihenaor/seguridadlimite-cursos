FROM node:18-alpine AS builder

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:alpine

COPY --from=builder /app/build /usr/share/nginx/html

COPY --from=builder /app/public/logo192.jpg /usr/share/nginx/html/logo192.png
COPY --from=builder /app/public/logo512.jpg /usr/share/nginx/html/logo512.png

RUN for f in /usr/share/nginx/html/static/js/*.js.gz /usr/share/nginx/html/static/css/*.css.gz; do [ -f "$f" ] && gunzip -f "$f"; done || true

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]