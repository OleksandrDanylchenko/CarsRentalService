import axios from 'axios';
import {store} from '../store'

class ModelDataService {
    retrieveAllModels() {
        return axios.get( store.state.apiUrl + `/models`);
    }
    retrieveModel(id) {
        return axios.get( store.state.apiUrl + `/models/${id}`);
    }
    addModel(newModel, newModelImage) {
        const addForm = new FormData();
        addForm.append('newModel', JSON.stringify(newModel));
        addForm.append('newModelImage', newModelImage);
        console.log(addForm);
        return axios.post(store.state.apiUrl + `/models`, addForm);
    }
    updateModel(updateModel, updateModelImage) {
        return axios.put(store.state.apiUrl + `/models`, updateModel, updateModelImage);
    }
    deleteModel(id) {
        return axios.delete( store.state.apiUrl + `/models/${id}`)
    }
}

export default new ModelDataService();