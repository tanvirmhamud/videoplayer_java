import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'videoplayer_method_channel.dart';

abstract class VideoplayerPlatform extends PlatformInterface {
  /// Constructs a VideoplayerPlatform.
  VideoplayerPlatform() : super(token: _token);

  static final Object _token = Object();

  static VideoplayerPlatform _instance = MethodChannelVideoplayer();

  /// The default instance of [VideoplayerPlatform] to use.
  ///
  /// Defaults to [MethodChannelVideoplayer].
  static VideoplayerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [VideoplayerPlatform] when
  /// they register themselves.
  static set instance(VideoplayerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion({required String url}) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
