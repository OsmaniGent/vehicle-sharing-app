# Stage 1: Build the application
FROM node:16-alpine as build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json (if available)
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application source code
COPY . .

# Build the application in production mode
RUN npm run build

# Verify that the build output is created
RUN ls -la /app/build

# Stage 2: Serve the application
FROM nginx:alpine

# Copy the built application from the previous stage
COPY --from=build /app/build /usr/share/nginx/html

# Copy nginx configuration file
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 3000
EXPOSE 3000

# Start nginx server
CMD ["nginx", "-g", "daemon off;"]
