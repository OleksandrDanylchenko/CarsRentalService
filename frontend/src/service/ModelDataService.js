import axios from 'axios';
import {store} from '../store'

class ModelDataService {
    retrieveAllModels() {
        return axios.get( store.state.apiUrl + `/models`);
    }
    retrieveModel(id) {
        return axios.get( store.state.apiUrl + `/models/${id}`);
    }
    addModel(newModel) {
        return axios.post(store.state.apiUrl + `/models`, newModel);
    }
    updateModel(updateModel) {
        return axios.put(store.state.apiUrl + `/models`, updateModel);
    }
    deleteModel(id) {
        return axios.delete( store.state.apiUrl + `/models/${id}`)
    }
}

export default new ModelDataService();