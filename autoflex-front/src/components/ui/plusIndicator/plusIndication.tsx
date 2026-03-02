import { FaPlus } from "react-icons/fa";
import { IconContext } from "react-icons";

export function PlusIndicator() {
  return (
    <div className="plus-indicator-container">
      <IconContext.Provider
          value={{ color: 'white', size: '20px' }}
        >
        <div>
          <FaPlus />
        </div>
      </IconContext.Provider>
    </div>
  );
}

export default PlusIndicator;