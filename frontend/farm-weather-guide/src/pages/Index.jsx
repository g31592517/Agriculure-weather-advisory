import { useState, useRef, useEffect } from "react";
import HeroSection from "../components/HeroSection";
import CountySelector from "../components/CountySelector";
import WeatherAdvisory from "../components/WeatherAdvisory";
import SmsSubscription from "../components/SmsSubscription";

const Index = () => {
  const [selectedCounty, setSelectedCounty] = useState("");
  const advisoryRef = useRef(null);

  const handleCountyChange = (county) => {
    setSelectedCounty(county);
  };

  useEffect(() => {
    if (selectedCounty && advisoryRef.current) {
      // Smooth scroll to advisory section when a county is selected
      setTimeout(() => {
        advisoryRef.current.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      }, 100);
    }
  }, [selectedCounty]);

  return (
    <div className="min-h-screen bg-background">
      <HeroSection />
      <CountySelector 
        selectedCounty={selectedCounty} 
        onCountyChange={handleCountyChange} 
      />
      <div ref={advisoryRef}>
        <WeatherAdvisory selectedCounty={selectedCounty} />
      </div>
      <SmsSubscription selectedCounty={selectedCounty} />
    </div>
  );
};

export default Index;
