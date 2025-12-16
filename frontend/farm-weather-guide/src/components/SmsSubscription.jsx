import { useState } from "react";
import { Bell, BellOff, Phone, Check, Loader2 } from "lucide-react";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { kenyanCounties } from "../data/kenyanCounties";
import { useToast } from "@/hooks/use-toast";

const SmsSubscription = ({ selectedCounty }) => {
  const [phoneNumber, setPhoneNumber] = useState("");
  const [subscriptionCounty, setSubscriptionCounty] = useState(selectedCounty || "");
  const [loading, setLoading] = useState(false);
  const [subscriptionStatus, setSubscriptionStatus] = useState(null);
  const { toast } = useToast();

  // Update subscription county when selected county changes
  useState(() => {
    if (selectedCounty) {
      setSubscriptionCounty(selectedCounty);
    }
  }, [selectedCounty]);

  const validatePhone = (phone) => {
    // Kenyan phone number validation (07XX or 01XX format, or +254)
    const kenyanPhoneRegex = /^(\+254|0)[17]\d{8}$/;
    return kenyanPhoneRegex.test(phone.replace(/\s/g, ""));
  };

  const handleSubscribe = async () => {
    if (!phoneNumber || !subscriptionCounty) {
      toast({
        title: "Missing Information",
        description: "Please enter your phone number and select a county.",
        variant: "destructive",
      });
      return;
    }

    if (!validatePhone(phoneNumber)) {
      toast({
        title: "Invalid Phone Number",
        description: "Please enter a valid Kenyan phone number (e.g., 0712345678).",
        variant: "destructive",
      });
      return;
    }

    setLoading(true);
    
    try {
      const response = await fetch("http://localhost:8080/api/subscribe", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          phoneNumber: phoneNumber,
          county: subscriptionCounty,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || "Failed to subscribe");
      }

      setSubscriptionStatus("subscribed");

      toast({
        title: "Successfully Subscribed!",
        description: `You will receive SMS alerts for ${subscriptionCounty} county.`,
      });
    } catch (error) {
      toast({
        title: "Subscription Failed",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleUnsubscribe = async () => {
    setLoading(true);
    
    try {
      const response = await fetch("http://localhost:8080/api/unsubscribe", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          phoneNumber: phoneNumber,
          county: subscriptionCounty,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || "Failed to unsubscribe");
      }

      setSubscriptionStatus(null);
      setPhoneNumber("");

      toast({
        title: "Unsubscribed",
        description: "You have been unsubscribed from SMS alerts.",
      });
    } catch (error) {
      toast({
        title: "Unsubscribe Failed",
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="py-8 px-4 pb-16">
      <div className="container mx-auto max-w-md">
        <Card className="border-border shadow-lg">
          <CardHeader className="text-center">
            <div className="mx-auto bg-primary/10 w-12 h-12 rounded-full flex items-center justify-center mb-3">
              <Bell className="h-6 w-6 text-primary" />
            </div>
            <CardTitle className="text-foreground">SMS Weather Alerts</CardTitle>
            <CardDescription>
              Receive farming advisories directly to your phone
            </CardDescription>
          </CardHeader>
          
          <CardContent className="space-y-4">
            {subscriptionStatus === "subscribed" ? (
              <div className="text-center py-4">
                <div className="mx-auto bg-primary/10 w-16 h-16 rounded-full flex items-center justify-center mb-4">
                  <Check className="h-8 w-8 text-primary" />
                </div>
                <h3 className="font-semibold text-foreground mb-2">You're Subscribed!</h3>
                <p className="text-sm text-muted-foreground mb-4">
                  Receiving alerts for {subscriptionCounty} county
                </p>
                <Button
                  variant="outline"
                  onClick={handleUnsubscribe}
                  disabled={loading}
                  className="gap-2"
                >
                  {loading ? (
                    <Loader2 className="h-4 w-4 animate-spin" />
                  ) : (
                    <BellOff className="h-4 w-4" />
                  )}
                  Unsubscribe
                </Button>
              </div>
            ) : (
              <>
                <div className="space-y-2">
                  <Label htmlFor="phone" className="text-foreground">Phone Number</Label>
                  <div className="relative">
                    <Phone className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                    <Input
                      id="phone"
                      type="tel"
                      placeholder="0712345678"
                      value={phoneNumber}
                      onChange={(e) => setPhoneNumber(e.target.value)}
                      className="pl-10 bg-background"
                    />
                  </div>
                  <p className="text-xs text-muted-foreground">
                    Enter your Kenyan mobile number
                  </p>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="sms-county" className="text-foreground">County for Alerts</Label>
                  <Select 
                    value={subscriptionCounty} 
                    onValueChange={setSubscriptionCounty}
                  >
                    <SelectTrigger id="sms-county" className="bg-background">
                      <SelectValue placeholder="Select county..." />
                    </SelectTrigger>
                    <SelectContent className="bg-popover border-border max-h-64">
                      {kenyanCounties.map((county) => (
                        <SelectItem key={county} value={county}>
                          {county}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>

                <Button
                  onClick={handleSubscribe}
                  disabled={loading || !phoneNumber || !subscriptionCounty}
                  className="w-full gap-2"
                >
                  {loading ? (
                    <Loader2 className="h-4 w-4 animate-spin" />
                  ) : (
                    <Bell className="h-4 w-4" />
                  )}
                  Subscribe to Alerts
                </Button>
              </>
            )}
          </CardContent>
        </Card>
      </div>
    </section>
  );
};

export default SmsSubscription;
