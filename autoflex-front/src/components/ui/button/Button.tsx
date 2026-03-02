import type { ReactNode } from "react";
import "./Button.css";

interface ButtonProps {
  title?: string;
  onClick?: () => void;
  backgroundColor: string;
  color: string;
  disabled?: boolean;
  children?: ReactNode;
}

export function Button({
  title,
  onClick,
  backgroundColor,
  color,
  disabled,
  children
}: ButtonProps) {
  return (
    <button 
      onClick={onClick} 
      disabled={disabled} 
      style={{ 
        backgroundColor: backgroundColor, 
        color: color,
        opacity: disabled ? 0.5 : 1,
        cursor: disabled ? "not-allowed" : "pointer" 
      }}
    >
      {children ?? title}
    </button>
  );
}

export default Button;