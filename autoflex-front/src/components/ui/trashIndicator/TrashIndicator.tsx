import { FaTrash } from "react-icons/fa6";
import { IconContext } from "react-icons";

export function TrashIndicator() {
  return (
    <div className="pencil-indicator-container">
      <IconContext.Provider
          value={{ color: 'red', size: '20px' }}
        >
        <div>
          <FaTrash />
        </div>
      </IconContext.Provider>
    </div>
  );
}

export default TrashIndicator;