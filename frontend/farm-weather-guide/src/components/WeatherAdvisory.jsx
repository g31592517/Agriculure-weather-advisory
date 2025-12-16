import { useState, useEffect } from "react";
import { Sun, Cloud, CloudRain, Thermometer, Wind, Droplets, AlertTriangle, Leaf } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";

// Fetch advisory from backend API
const fetchAdvisory = async (county) => {
  const response = await fetch(`http://localhost:8080/api/advisory/${county}`);
  if (!response.ok) {
    throw new Error(`Failed to fetch advisory: ${response.statusText}`);
  }

  const data = await response.json();

  // Transform backend response to match component structure
  return {
    weather: {
      temperature: Math.round(data.temperature),
      condition: data.weatherCondition,
      humidity: Math.round(data.humidity),
      windSpeed: 0, // Not provided by backend yet
    },
    advisory: {
      title: "Farming Advisory",
      message: data.advisoryText,
      severity: determineSeverity(data.temperature, data.humidity),
    },
  };
};

const determineSeverity = (temp, humidity) => {
  if (temp > 30 || humidity > 85 || temp < 15) return "high";
  if (temp > 28 || humidity > 75 || temp < 18) return "medium";
  return "low";
};

const getWeatherIcon = (condition) => {
  const conditionLower = condition.toLowerCase();

  if (conditionLower.includes("clear") || conditionLower.includes("sunny")) {
    return <Sun className="h-12 w-12 text-secondary" />;
  } else if (conditionLower.includes("rain") || conditionLower.includes("drizzle")) {
    return <CloudRain className="h-12 w-12 text-accent" />;
  } else if (conditionLower.includes("cloud")) {
    return <Cloud className="h-12 w-12 text-muted-foreground" />;
  } else {
    return <Cloud className="h-12 w-12 text-muted-foreground" />;
  }
};

const getSeverityStyles = (severity) => {
  switch (severity) {
    case "high":
      return "bg-destructive/10 border-destructive/30 text-destructive";
    case "medium":
      return "bg-secondary/20 border-secondary/50 text-secondary-foreground";
    default:
      return "bg-primary/10 border-primary/30 text-primary";
  }
};

const WeatherAdvisory = ({ selectedCounty }) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log("WeatherAdvisory - Selected County:", selectedCounty);

    if (!selectedCounty) {
      setData(null);
      return;
    }

    const loadData = async () => {
      console.log("Starting API call for county:", selectedCounty);
      setLoading(true);
      setError(null);
      try {
        const apiUrl = `http://localhost:8080/api/advisory/${selectedCounty}`;
        console.log("API URL:", apiUrl);

        const result = await fetchAdvisory(selectedCounty);
        console.log("API Response received:", result);
        setData(result);
      } catch (err) {
        console.error("API Error:", err);
        setError("Failed to fetch advisory data. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [selectedCounty]);

  if (!selectedCounty) {
    return (
      <section className="py-8 px-4">
        <div className="container mx-auto max-w-2xl">
          <Card className="border-2 border-dashed border-primary/30 bg-gradient-to-br from-primary/5 to-accent/5">
            <CardContent className="py-16 text-center">
              <div className="relative inline-block mb-6">
                <div className="absolute inset-0 bg-primary/10 rounded-full blur-xl"></div>
                <div className="relative bg-primary/10 p-6 rounded-full">
                  <Cloud className="h-12 w-12 text-primary" />
                </div>
              </div>
              <h3 className="text-xl font-semibold text-foreground mb-2">
                No Advisory Loaded Yet
              </h3>
              <p className="text-muted-foreground max-w-sm mx-auto mb-6">
                Select a county from the dropdown above to view current weather conditions and personalized agricultural advisories.
              </p>
              <div className="flex items-center justify-center gap-2 text-sm text-primary font-medium">
                <svg className="h-4 w-4 animate-bounce" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 10l7-7m0 0l7 7m-7-7v18" />
                </svg>
                <span>Choose your county to get started</span>
              </div>
            </CardContent>
          </Card>
        </div>
      </section>
    );
  }

  if (loading) {
    return (
      <section className="py-8 px-4">
        <div className="container mx-auto max-w-2xl space-y-4">
          <Card>
            <CardHeader>
              <Skeleton className="h-6 w-48" />
            </CardHeader>
            <CardContent className="space-y-4">
              <Skeleton className="h-20 w-full" />
              <Skeleton className="h-4 w-3/4" />
            </CardContent>
          </Card>
        </div>
      </section>
    );
  }

  if (error) {
    return (
      <section className="py-8 px-4">
        <div className="container mx-auto max-w-2xl">
          <Card className="border-destructive/30 bg-destructive/5">
            <CardContent className="py-8 text-center">
              <AlertTriangle className="h-12 w-12 text-destructive mx-auto mb-4" />
              <p className="text-destructive">{error}</p>
            </CardContent>
          </Card>
        </div>
      </section>
    );
  }

  // Guard clause: Don't render data UI if data doesn't exist yet
  if (!data || !data.weather || !data.advisory) {
    return null; // This will show nothing while data is being fetched
  }

  return (
    <section className="py-8 px-4">
      <div className="container mx-auto max-w-2xl space-y-6">
        {/* Weather Summary Card */}
        <Card className="overflow-hidden">
          <CardHeader className="bg-primary/5 border-b border-border">
            <CardTitle className="flex items-center gap-2 text-foreground">
              <Thermometer className="h-5 w-5 text-primary" />
              Weather in {selectedCounty}
            </CardTitle>
          </CardHeader>
          <CardContent className="pt-6">
            <div className="flex flex-col md:flex-row items-center gap-6">
              <div className="flex items-center gap-4">
                {getWeatherIcon(data.weather.condition)}
                <div>
                  <p className="text-4xl font-bold text-foreground">
                    {data.weather.temperature}°C
                  </p>
                  <p className="text-muted-foreground">{data.weather.condition}</p>
                </div>
              </div>
              
              <div className="flex gap-6 md:ml-auto">
                <div className="flex items-center gap-2">
                  <Droplets className="h-5 w-5 text-accent" />
                  <div>
                    <p className="text-sm text-muted-foreground">Humidity</p>
                    <p className="font-medium text-foreground">{data.weather.humidity}%</p>
                  </div>
                </div>
                <div className="flex items-center gap-2">
                  <Wind className="h-5 w-5 text-muted-foreground" />
                  <div>
                    <p className="text-sm text-muted-foreground">Wind</p>
                    <p className="font-medium text-foreground">{data.weather.windSpeed} km/h</p>
                  </div>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Advisory Card */}
        <Card className={`border-2 ${getSeverityStyles(data.advisory.severity)}`}>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Leaf className="h-5 w-5" />
              {data.advisory.title}
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-base leading-relaxed">
              {data.advisory.message}
            </p>
          </CardContent>
        </Card>
      </div>
    </section>
  );
};

export default WeatherAdvisory;
