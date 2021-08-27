from kivy.app import App
from kivy.lang.builder import Builder
from bioo import Bioo, run_on_ui_thread
from kivy.properties import StringProperty


KV = """
BoxLayout:
    orientation: "vertical"
    ScrollableLabel:
        text: str(app.some_string)
        font_size: 80
        text_size: self.width, None
        size_hint_y: None
        height: self.texture_size[1]
    Button:
        text: "launch bio"
        on_release: app.auth()
<ScrollableLabel@Label+ScrollView>
"""


class TestApp(App):
    some_string = StringProperty(rebind=True)
    def on_start(self):
        if str(Bioo().get_auth()) == "0":
            App.get_running_app().some_string = "device has fingerprint sensor"

    def auth(self):
        Bioo().auth_now(self.my_auth_callback)
    
    def my_auth_callback(self, args):
        App.get_running_app().some_string = str(args)

    def build(self):
        return Builder.load_string(KV)





TestApp().run()
