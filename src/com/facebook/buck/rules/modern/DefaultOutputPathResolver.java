/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.buck.rules.modern;

import com.facebook.buck.core.filesystems.RelPath;
import com.facebook.buck.core.model.BuildTarget;
import com.facebook.buck.core.model.impl.BuildPaths;
import com.facebook.buck.io.filesystem.ProjectFilesystem;
import java.nio.file.Path;

/** Default OutputPathResolver implementation. */
public class DefaultOutputPathResolver implements OutputPathResolver {
  private final RelPath scratchRoot;
  private final RelPath genRoot;

  public DefaultOutputPathResolver(ProjectFilesystem projectFilesystem, BuildTarget buildTarget) {
    this.scratchRoot = BuildPaths.getScratchDir(projectFilesystem, buildTarget);
    this.genRoot = BuildPaths.getGenDir(projectFilesystem, buildTarget);
  }

  @Override
  public Path getTempPath() {
    return scratchRoot.getPath();
  }

  @Override
  public Path resolvePath(OutputPath outputPath) {
    if (outputPath instanceof PublicOutputPath) {
      return outputPath.getPath();
    }
    return getRootPath().resolve(outputPath.getPath());
  }

  @Override
  public Path getRootPath() {
    return genRoot.getPath();
  }
}
