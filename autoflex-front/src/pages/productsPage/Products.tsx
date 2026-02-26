import { Table } from "../../components/ui/table/Table";
import "./Products.css";

function Products() {  

  return (
    <Table
        headers={["Empresa", "Contato", "País", "Opções"]}
        data={[
            ["Alfreds Futterkiste", "Maria Anders", "Germany"],
            ["Centro comercial Moctezuma", "Francisco Chang", "Mexico"],
        ]}
    />
  );
}

export default Products;