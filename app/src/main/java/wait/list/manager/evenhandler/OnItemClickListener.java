package wait.list.manager.evenhandler;

public interface OnItemClickListener {
    void onItemClick(long id);
    void onItemEditClick(long id, int position);
    void onItemDeleteClick(long id, int position);
    void onItemStatusChange(long id, int position);

    void OnItemClickListenerWithShareOption(long id, int position);
}