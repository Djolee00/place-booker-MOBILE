FROM node:16 as builder

RUN npm install -g @ionic/cli

WORKDIR /app

COPY ./package.json ./package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:alpine

COPY --from=builder /app/www /usr/share/nginx/html