import NoIndicator from "../ui/noIndicator/NoIndicator";
import YesIndicator from "../ui/yesIndicator/YesIndicator";

type BooleanIndicatorProps = {
  value: boolean;
};

export function BooleanIndicator({ value }: BooleanIndicatorProps) {
  return value ? <YesIndicator /> : <NoIndicator />;
}