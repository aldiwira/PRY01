package ac.id.polinema.owner.ui;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class RecyclerViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> implements
        DraggableModule {

    private Bind<T> binding;

    public interface Bind<T> {
        void bind(BaseViewHolder holder, T t);
    }

    public RecyclerViewAdapter(int layoutResId, List<T> data, Bind<T> bindView) {
        super(layoutResId, data);
        this.binding = bindView;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, T t) {
        binding.bind(baseViewHolder, t);
    }
}
