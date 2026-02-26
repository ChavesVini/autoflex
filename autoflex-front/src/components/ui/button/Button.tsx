import "./Button.css";

interface ButtonProps {
  title: string;
  onClick: () => void;
  backgroundColor: string;
  color: string;
}

export function Button({
  title,
  onClick,
  backgroundColor,
  color
}: ButtonProps) {
  return (
    <button onClick={onClick} style={{ backgroundColor: backgroundColor, color: color }}>
      {title}
    </button>
  );
}

export default Button;