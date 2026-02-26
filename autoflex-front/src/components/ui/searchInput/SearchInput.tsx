import "./SearchInput.css";
import { FaSearch } from "react-icons/fa";

export function SearchInput() {
  return (
    <div className="search-container">
      <FaSearch className="search-icon" />
      <input
        type="search"
        className="search-input"
        placeholder="Pesquisar..."
      />
    </div>
  );
}

export default SearchInput;