import { Button } from "../button/Button";
import "./SearchInput.css";
import { FaSearch } from "react-icons/fa";

interface SearchInputProps {
  value: string;
  onChange: (value: string) => void;
  onSearch: () => void
}

export function SearchInput({ value, onChange, onSearch }: SearchInputProps) {
  return (
    <div className="search-container">
      <FaSearch className="search-icon" onClick={onSearch} />
      <input
        className="search-input"
        type="text"
        placeholder="Search..."
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
}

export default SearchInput;