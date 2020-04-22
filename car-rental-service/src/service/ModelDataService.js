import axios from 'axios';
import {store} from '../store'

class ModelDataService {
    retrieveAllModels() {
        return axios.get( store.state.apiUrl + `/model`);
    }
    retrieveModel(id) {
        return axios.get( store.state.apiUrl + `/model/${id}`);
    }
    addModel(newModel) {
        return axios.post(store.state.apiUrl + `/model`, newModel);
    }
    updateModel(updateModel) {
        return axios.put(store.state.apiUrl + `/model`, updateModel);
    }
    deleteModel(id) {
        return axios.delete( store.state.apiUrl + `/model/${id}`)
    }
}

export default new ModelDataService();