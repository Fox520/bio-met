from kivy.clock import Clock, mainthread
from jnius import autoclass, java_method, PythonJavaClass

class Bioo:
    biometric_handler = None
    def __init__(self):
        self.biometric_handler = autoclass("org.kivy.plugins.authentication.BiometricHandler")
    
    def get_auth(self):
        return self.biometric_handler.checkFingerprintSensor()
    
    def auth_now(self, callback):
        PythonActivity = autoclass("org.kivy.android.PythonActivity")
        mactivity = PythonActivity.mActivity
        randoms_map = {"5": "nothing", "-1": "authenticating", "2": "error", "0": "success", "1": "auth failed", "13": "user cancelled"}
        def checker(*args):
            global event
            if self.biometric_handler is None:
                return
            rr = randoms_map[str(self.biometric_handler.randomNumber)]
            if str(self.biometric_handler.errorMessage) != "":
                rr = self.biometric_handler.errorMessage
            callback(rr)
        def call():
            self.biometric_handler.authenticate(mactivity.getApplicationContext(), mactivity)
        # Figure out how to cancel event on result
        self.event = Clock.schedule_interval(checker, 0.3)
        run_on_ui_thread(call)

def run_on_ui_thread(func):
    class MyRunnable(PythonJavaClass):
        __javainterfaces__ = ['java/lang/Runnable']
        @java_method("()V")
        def run(self):
            func()
    Handler = autoclass('android.os.Handler')
    Looper = autoclass('android.os.Looper')
    Handler(Looper.getMainLooper()).post(MyRunnable())
