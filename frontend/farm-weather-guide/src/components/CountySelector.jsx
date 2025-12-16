import { MapPin } from "lucide-react";
import { kenyanCounties } from "../data/kenyanCounties";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Label } from "@/components/ui/label";

const CountySelector = ({ selectedCounty, onCountyChange }) => {
  const handleChange = (value) => {
    console.log("County Selected:", value);
    onCountyChange(value);
  };

  return (
    <section className="py-8 px-4">
      <div className="container mx-auto max-w-md">
        <div className="bg-card rounded-xl border border-border p-6 shadow-sm">
          <div className="flex items-center gap-2 mb-4">
            <MapPin className="h-5 w-5 text-primary" />
            <Label htmlFor="county-select" className="text-lg font-semibold text-foreground">
              Select County
            </Label>
          </div>
          
          <Select value={selectedCounty} onValueChange={handleChange}>
            <SelectTrigger id="county-select" className="w-full bg-background">
              <SelectValue placeholder="Choose a county to view advisory..." />
            </SelectTrigger>
            <SelectContent className="bg-popover border-border max-h-64">
              {kenyanCounties.map((county) => (
                <SelectItem key={county} value={county}>
                  {county}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
          
          {selectedCounty && (
            <p className="mt-3 text-sm text-muted-foreground">
              Currently viewing: <span className="font-medium text-primary">{selectedCounty}</span>
            </p>
          )}
        </div>
      </div>
    </section>
  );
};

export default CountySelector;
