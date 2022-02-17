import { useState } from "react";

function Category(props){
    const [category, setCategory] = useState(props.category)

    function checkHandler(event){
        let isChecked = category.checked;
        isChecked = !isChecked;
        setCategory((prev) => {
            return {
                ...prev,
                checked: event.target.checked
            }
        })
        // console.log(category.name, category.checked);
        props.onInputCheckHandler(category.name, event.target.checked);
    }
    
    return (
        <div>
            <table>
                <tr>
                    <td>{category.name}</td>
                    <td><input type='checkbox' checked={category.checked} onChange={checkHandler} /></td>
                </tr>
            </table>
        </div>
    )
}

export default Category;