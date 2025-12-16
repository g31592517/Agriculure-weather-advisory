# Agricultural Weather Advisory Backend

Simple Spring Boot backend for the Agricultural Weather Advisory System.

## Prerequisites
- Java 17+
- Maven 3.6+
- MongoDB (running on localhost:27017)

## Setup & Run

1. **Start MongoDB:**
   ```bash
   # Linux/Mac
   sudo systemctl start mongod
   
   # Or use Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

   The server will start on `http://localhost:8080`

## API Endpoints

### Get Weather Advisory
```
GET /api/advisory/{county}

Example: GET http://localhost:8080/api/advisory/Nairobi

Response:
{
  "id": "...",
  "county": "Nairobi",
  "weatherCondition": "Clear",
  "temperature": 24.5,
  "humidity": 65.0,
  "advisoryText": "Optimal temperature for most crops. Good conditions for planting...",
  "createdAt": "2025-01-15T10:30:00"
}
```

### Subscribe to SMS Alerts
```
POST /api/subscribe
Content-Type: application/json

{
  "phoneNumber": "0712345678",
  "county": "Nairobi"
}

Response:
{
  "status": "success",
  "message": "Successfully subscribed to Nairobi alerts"
}
```

### Unsubscribe from Alerts
```
POST /api/unsubscribe
Content-Type: application/json

{
  "phoneNumber": "0712345678",
  "county": "Nairobi"
}

Response:
{
  "status": "success",
  "message": "Successfully unsubscribed from Nairobi alerts"
}
```

## Supported Counties

All 47 Kenyan counties are supported:
- Nairobi, Mombasa, Kisumu, Nakuru, Eldoret
- Kiambu, Machakos, Kajiado, Kilifi, Kwale
- And 37 more...

## Features

✅ Real-time weather data from OpenWeatherMap API  
✅ Smart farming advisories based on temperature, humidity, and weather conditions  
✅ SMS subscription simulation (logs to console)  
✅ MongoDB storage for advisories and subscriptions  
✅ CORS enabled for React frontend  
✅ All 47 Kenyan counties supported  

## Project Structure

```
src/main/java/com/weather/advisory/
├── AgriculturalWeatherBackendApplication.java  # Main app
├── Advisory.java                               # MongoDB document
├── Subscription.java                           # MongoDB document
├── AdvisoryRepository.java                     # Data access
├── SubscriptionRepository.java                 # Data access
├── CountyCoordinates.java                      # County lat/lon mapping
├── WeatherApiClient.java                       # Feign client
├── WeatherResponse.java                        # API response DTO
├── WeatherService.java                         # Weather fetching
├── AdvisoryService.java                        # Advisory generation
├── SubscriptionService.java                    # SMS subscription logic
├── AdvisoryController.java                     # REST endpoint
├── SubscriptionController.java                 # REST endpoint
└── WebConfig.java                              # CORS configuration
```

## Testing the API

Use curl or Postman:

```bash
# Get advisory for Nairobi
curl http://localhost:8080/api/advisory/Nairobi

# Subscribe to alerts
curl -X POST http://localhost:8080/api/subscribe \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber":"0712345678","county":"Nairobi"}'

# Unsubscribe
curl -X POST http://localhost:8080/api/unsubscribe \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber":"0712345678","county":"Nairobi"}'
```

## Notes

- Advisory data is cached for 30 minutes to avoid excessive API calls
- SMS sending is simulated (logs to console). See logs for "📱 SMS SIMULATION 📱"
- Valid Kenyan phone formats: 0712345678, 0112345678, +254712345678
- OpenWeatherMap free tier API key is already configured

## MongoDB Collections

- `advisories` - Stores weather advisories
- `subscriptions` - Stores user SMS subscriptions

## Troubleshooting

**MongoDB Connection Error:**
- Ensure MongoDB is running: `sudo systemctl status mongod`
- Check connection string in `application.properties`

**API Key Error:**
- The free tier API key is already configured
- If it fails, the key might need activation (takes a few hours)

**CORS Issues:**
- Frontend should run on http://localhost:5173 or http://localhost:3000
- Other ports need to be added to WebConfig.java

