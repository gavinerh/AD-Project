import { useState } from "react";

function Category(props) {
    const [category, setCategory] = useState(props.category)

    console.log(props.category);
    function checkHandler(event) {
        let isChecked = category.checked;
        isChecked = !isChecked;
        setCategory((prev) => {
            return {
                ...prev,
                select: event.target.checked
            }
        })
        // console.log(category.name, category.checked);
        props.onInputCheckHandler(category.name, event.target.checked);
    }

    return (
        <div className="form-check">
            <input className="form-check-input" type="checkbox" id="flexCheckDefault" checked={category.select} onChange={checkHandler} />
            <label className="form-check-label" htmlFor="flexCheckDefault">{category.name}</label>
        </div>

        // <div>
        //     <table>
        //         <tr>
        //             <td>{category.name}</td>
        //             <td><input type='checkbox' checked={category.checked} onChange={checkHandler} /></td>
        //         </tr>
        //     </table>
        // </div>
    )
}

export default Category;