import { CloudSun, Leaf, Bell } from "lucide-react";

const HeroSection = () => {
  return (
    <section className="bg-gradient-to-br from-primary/10 via-background to-secondary/10 py-12 px-4 md:py-20">
      <div className="container mx-auto max-w-4xl text-center">
        <div className="flex justify-center gap-3 mb-6">
          <CloudSun className="h-12 w-12 text-primary" />
          <Leaf className="h-12 w-12 text-primary" />
        </div>
        
        <h1 className="text-3xl md:text-5xl font-bold text-foreground mb-4">
          Agricultural Weather Advisory System
        </h1>
        
        <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto mb-8">
          Get real-time weather updates and farming advice for your county. 
          Select a Kenyan county to view current weather conditions, receive 
          agricultural advisories, and subscribe to SMS alerts.
        </p>
        
        <div className="flex flex-wrap justify-center gap-4 text-sm text-muted-foreground">
          <div className="flex items-center gap-2 bg-card px-4 py-2 rounded-lg border border-border">
            <CloudSun className="h-5 w-5 text-accent" />
            <span>Weather Updates</span>
          </div>
          <div className="flex items-center gap-2 bg-card px-4 py-2 rounded-lg border border-border">
            <Leaf className="h-5 w-5 text-primary" />
            <span>Farming Advice</span>
          </div>
          <div className="flex items-center gap-2 bg-card px-4 py-2 rounded-lg border border-border">
            <Bell className="h-5 w-5 text-secondary-foreground" />
            <span>SMS Alerts</span>
          </div>
        </div>
      </div>
    </section>
  );
};

export default HeroSection;
