import React, { useEffect, useState } from 'react';
import DataGrid, {
    Column,
    Editing,
  } from 'devextreme-react/data-grid';
import { getCategories, createCategory, deleteCategory } from './../service/CategoryService';

const AdminCategory = () => {

    const [dataSource, setDataSource] = useState([]);

    useEffect(() => {
        getCategories()
            .then(response => {
                setDataSource(response.data);
            });
    }, []);

    const onRowInserting = (event) => {
        const cancelEvent = new Promise((resolve) => {
            createCategory(event.data.name).then(() => {
                resolve(false);
                getCategories()
                    .then(response => {
                        setDataSource(response.data);
                    });
            });
        });

        event.cancel = cancelEvent;
    };

    const onRowRemoving = (event) => {
        const cancelEvent = new Promise((resolve) => {
            deleteCategory(event.data.id).then(() => {
                const newDataSource = dataSource.filter(it => it.id !== event.data.id);
                setDataSource(newDataSource);
                resolve(false);
            });
        });

        event.cancel = cancelEvent;
    }

    return (
        <>
            {
                dataSource ? (
                    <DataGrid 
                    dataSource={dataSource}
                    onRowInserting={onRowInserting}
                    onRowRemoving={onRowRemoving}
                    >
                        <Editing
                            mode="row"
                            allowDeleting={true}
                            allowAdding={true} 
                        />
                        <Column dataField="name" caption="Name" />
                    </DataGrid>
                ) : <></>
            }
        </>
    );
}

export default AdminCategory;