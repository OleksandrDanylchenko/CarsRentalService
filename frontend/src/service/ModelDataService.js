import axios from 'axios';
import {store} from '../store'

class ModelDataService {
    retrieveAllModels() {
        return axios.get( store.state.apiUrl + `/models`);
    }
    retrieveModel(id) {
        return axios.get( store.state.apiUrl + `/models/${id}`);
    }
    addModel(addForm) {
        return axios.post(store.state.apiUrl + `/models`, addForm);
    }
    updateModel(updateForm) {
        return axios.put(store.state.apiUrl + `/models`, updateForm);
    }
    deleteModel(id) {
        return axios.delete( store.state.apiUrl + `/models/${id}`)
    }
}

export default new ModelDataService();