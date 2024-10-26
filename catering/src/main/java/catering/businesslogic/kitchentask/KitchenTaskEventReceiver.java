package catering.businesslogic.kitchentask;

public interface KitchenTaskEventReceiver {
    void updateNewListCreated(ToDoList tdl);

    void updateListDeleted(ToDoList tdl);

    void updateNewTaskAdded(KitchenTask kitchenTask);

    void updateTaskRemoved(KitchenTask kitchenTask);

    void updateTaskChanged(KitchenTask kitchenTask);
}
