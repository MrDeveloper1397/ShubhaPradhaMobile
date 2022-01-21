package task.marami.Shubhaprada.Interfaces;

public interface ISlashScreen {
    interface SlashScreenPresenter{
        void onLoad();
    }
    interface SlashScreenView{
        void onHomeScr();
        void onLoginScr();
        void onOthersHomeScr();
    }
}
