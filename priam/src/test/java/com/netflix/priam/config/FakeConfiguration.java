/*
 * Copyright 2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.netflix.priam.config;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class FakeConfiguration implements IConfiguration {

    private final String appName;
    private String restorePrefix = "";

    public final Map<String, String> fakeProperties = new HashMap<>();

    public FakeConfiguration() {
        this("my_fake_cluster");
    }

    public FakeConfiguration(String appName) {
        this.appName = appName;
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getBackupLocation() {
        // TODO Auto-generated method stub
        return "casstestbackup";
    }

    @Override
    public String getBackupPrefix() {
        // TODO Auto-generated method stub
        return "TEST-netflix.platform.S3";
    }

    @Override
    public String getCommitLogLocation() {
        // TODO Auto-generated method stub
        return "cass/commitlog";
    }

    @Override
    public String getDataFileLocation() {
        // TODO Auto-generated method stub
        return "target/data";
    }

    @Override
    public String getLogDirLocation() {
        return null;
    }

    @Override
    public String getCacheLocation() {
        // TODO Auto-generated method stub
        return "cass/caches";
    }

    @Override
    public List<String> getRacs() {
        return Arrays.asList("az1", "az2", "az3");
    }

    @Override
    public String getSnitch() {
        return "org.apache.cassandra.locator.SimpleSnitch";
    }

    @Override
    public String getHeapSize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHeapNewSize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRestoreSnapshot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getACLGroupName() {
        return this.getAppName();
    }

    @Override
    public String getSDBInstanceIdentityRegion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getRestoreThreads() {
        return 2;
    }

    @Override
    public String getRestorePrefix() {
        return this.restorePrefix;
    }

    // For testing purposes only.
    public void setRestorePrefix(String restorePrefix) {
        this.restorePrefix = restorePrefix;
    }

    @Override
    public String getBackupCommitLogLocation() {
        return "cass/backup/cl/";
    }

    @Override
    public String getSiblingASGNames() {
        return null;
    }

    @Override
    public boolean isLocalBootstrapEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getBootClusterName() {
        // TODO Auto-generated method stub
        return "cass_bootstrap";
    }

    @Override
    public String getCassHome() {
        return "/tmp/priam";
    }

    @Override
    public String getCassStartupScript() {
        // TODO Auto-generated method stub
        return "/usr/bin/false";
    }

    @Override
    public long getBackupChunkSize() {
        return 5L * 1024 * 1024;
    }

    @Override
    public String getCassStopScript() {
        return "true";
    }

    @Override
    public int getRemediateDeadCassandraRate() {
        return 1;
    }

    @Override
    public String getSeedProviderName() {
        return "org.apache.cassandra.locator.SimpleSeedProvider";
    }

    @Override
    public int getBackupRetentionDays() {
        return 5;
    }

    @Override
    public List<String> getBackupRacs() {
        return Lists.newArrayList();
    }

    public String getPartitioner() {
        return "org.apache.cassandra.dht.RandomPartitioner";
    }

    public String getKeyCacheSizeInMB() {
        return "16";
    }

    public String getKeyCacheKeysToSave() {
        return "32";
    }

    public String getRowCacheSizeInMB() {
        return "4";
    }

    public String getRowCacheKeysToSave() {
        return "4";
    }

    @Override
    public String getCassProcessName() {
        return "CassandraDaemon";
    }

    public String getYamlLocation() {
        return "conf/cassandra.yaml";
    }

    @Override
    public boolean doesCassandraStartManually() {
        return false;
    }

    @Override
    public String getCommitLogBackupPropsFile() {
        return getCassHome() + "/conf/commitlog_archiving.properties";
    }

    @Override
    public String getCommitLogBackupArchiveCmd() {
        return null;
    }

    @Override
    public String getCommitLogBackupRestoreCmd() {
        return null;
    }

    @Override
    public String getCommitLogBackupRestoreFromDirs() {
        return null;
    }

    @Override
    public String getCommitLogBackupRestorePointInTime() {
        return null;
    }

    @Override
    public int maxCommitLogsRestore() {
        return 0;
    }

    public boolean isClientSslEnabled() {
        return true;
    }

    public String getInternodeEncryption() {
        return "all";
    }

    public boolean isDynamicSnitchEnabled() {
        return true;
    }

    public boolean isThriftEnabled() {
        return true;
    }

    public boolean isNativeTransportEnabled() {
        return false;
    }

    public int getConcurrentReadsCnt() {
        return 8;
    }

    public int getConcurrentWritesCnt() {
        return 8;
    }

    public int getConcurrentCompactorsCnt() {
        return 1;
    }

    @Override
    public int getCompactionLargePartitionWarnThresholdInMB() {
        return 100;
    }

    public String getExtraConfigParams() {
        return null;
    }

    public String getCassYamlVal(String priamKey) {
        return "";
    }

    @Override
    public boolean getAutoBoostrap() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCreateNewTokenEnable() {
        return true; // allow Junit test to create new tokens
    }

    @Override
    public String getPrivateKeyLocation() {
        return null;
    }

    @Override
    public String getRestoreSourceType() {
        return null;
    }

    @Override
    public String getAWSRoleAssumptionArn() {
        return null;
    }

    @Override
    public String getClassicEC2RoleAssumptionArn() {
        return null;
    }

    @Override
    public String getVpcEC2RoleAssumptionArn() {
        return null;
    }

    @Override
    public String getGcsServiceAccountId() {
        return null;
    }

    @Override
    public String getGcsServiceAccountPrivateKeyLoc() {
        return null;
    }

    @Override
    public String getPgpPasswordPhrase() {
        return null;
    }

    @Override
    public String getPgpPublicKeyLoc() {
        return null;
    }

    /**
     * Use this method for adding extra/ dynamic cassandra startup options or env properties
     *
     * @return
     */
    @Override
    public Map<String, String> getExtraEnvParams() {
        return null;
    }

    @Override
    public int getBackupQueueSize() {
        return 100;
    }

    @Override
    public String getFlushKeyspaces() {
        return "";
    }

    @Override
    public boolean isPostRestoreHookEnabled() {
        return true;
    }

    @Override
    public String getPostRestoreHook() {
        return "echo";
    }

    @Override
    public String getPostRestoreHookHeartbeatFileName() {
        return System.getProperty("java.io.tmpdir") + File.separator + "postrestorehook.heartbeat";
    }

    @Override
    public String getPostRestoreHookDoneFileName() {
        return System.getProperty("java.io.tmpdir") + File.separator + "postrestorehook.done";
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return fakeProperties.getOrDefault(key, defaultValue);
    }

    @Override
    public String getMergedConfigurationDirectory() {
        return fakeProperties.getOrDefault("priam_test_config", "/tmp/priam_test_config");
    }
}
