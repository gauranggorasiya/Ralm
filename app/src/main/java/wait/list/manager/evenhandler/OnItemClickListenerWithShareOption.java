package wait.list.manager.evenhandler;

public interface OnItemClickListenerWithShareOption extends OnItemClickListener {
    void onItemClick(long id);
    void onItemEditClick(long id, int position);
    void onItemDeleteClick(long id, int position);
    void onItemButtonAllocateClick(long id, int position);
    void onItemButtonDeallocateClick(long id, int position);
    void onItemStatusChange(long id, int position);
    void onItemShareButtonClicked(long id, long pos);

}