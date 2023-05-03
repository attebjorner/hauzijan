FROM node:14.15.4-alpine3.11
WORKDIR /app
ADD . .
RUN npm install -g serve
CMD serve -s build -p 8080

