// services/messageService.js
export const addMessageToMap = (prev, key, newMessage) => {
  return {
    ...prev,
    [key]: [...(prev[key] || []), newMessage],
  };
};