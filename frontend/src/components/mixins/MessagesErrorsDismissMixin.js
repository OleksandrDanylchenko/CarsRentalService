export const MessagesErrorsDismissMixin = {
    methods: {
        dismissMessages() {
            this.messages = [];
        },
        dismissErrors() {
            this.errors = [];
        }
    }
}